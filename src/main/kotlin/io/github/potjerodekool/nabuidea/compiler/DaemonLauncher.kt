package io.github.potjerodekool.nabuidea.compiler

import io.github.potjerodekool.nabu.compiler.daemon.LightweightCompilerDaemon
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.nio.file.Files
import java.nio.file.Paths
import java.util.logging.Level
import java.util.logging.Logger


class DaemonLauncher {

    private val logger = Logger.getLogger(DaemonLauncher::class.java.name)

    companion object {
        private const val HOST: String = "localhost"
        private const val PORT: Int = 9876
        private const val STARTUP_TIMEOUT: Int = 10 // seconds
    }

    private lateinit var daemonInstance: LightweightCompilerDaemon

    private lateinit var daemonThread : Thread

    private lateinit var daemonProcess: Process

    val isRunning = isDaemonRunning()

    fun start(): Boolean {
        startExternalProcess()
        return isDaemonRunning()
    }

    private fun waitForDaemon(timeoutSeconds: Int): Boolean {
        logger.info("Waiting for daemon to start")

        (0..< timeoutSeconds * 2).forEach { _ ->
            if (isDaemonRunning()) {
                logger.info(" ✓")
                return true
            }

            try {
                Thread.sleep(500)
            } catch (_: InterruptedException) {
                Thread.currentThread().interrupt()
                return false
            }
        }

        logger.severe(" x")
        return false
    }

    private fun isDaemonRunning(): Boolean {
        try {
            Socket().use { socket ->
                socket.connect(InetSocketAddress(HOST, PORT), 1000)
                return true
            }
        } catch (_: IOException) {
            return false
        }
    }

    fun waitUntilReady(timeoutSeconds: Int): Boolean {
        return waitForDaemon(timeoutSeconds)
    }

    fun stop() {
        stopDaemon()
    }

    fun stopDaemon() {
        logger.info("Stopping daemon...")

        // Probeer graceful shutdown via SHUTDOWN command
        if (isDaemonRunning()) {
            try {
                sendShutdownCommand()

                // Wacht tot daemon stopt
                (0..4).forEach {_ ->
                    Thread.sleep(1000)
                    if (!isDaemonRunning()) {
                        logger.info("✓ Daemon stopped gracefully")
                        deletePID();
                        return
                    }
                }

                logger.info("⚠ Daemon did not stop gracefully")
            } catch (e: Exception) {
                logger.log(
                    Level.SEVERE,
                    "Error during graceful shutdown: " + e.message,
                    e
                )
            }
        }

        // Force stop als thread
        daemonInstance.stop()

        if (daemonThread.isAlive) {
            daemonThread.interrupt()
        }

        logger.info("✓ Daemon stopped")
    }

    @Throws(IOException::class)
    private fun sendShutdownCommand() {
        Socket(HOST, PORT).use { socket ->
            DataOutputStream(socket.getOutputStream()).use { out ->
                DataInputStream(socket.getInputStream()).use { `in` ->

                    // Send SHUTDOWN (0xFF)
                    out.writeByte(0xFF)
                    out.flush()

                    // Read response
                    val status = `in`.readByte()
                    val length = `in`.readInt()
                    val data = ByteArray(length)
                    `in`.readFully(data)
                    logger.info("Server response: " + String(data, charset("UTF-8")))
                }
            }
        }
    }

    fun startExternalProcess(): Boolean {
        logger.info("Starting daemon as external process...")

        try {
            // Check of daemon al draait
            if (isDaemonRunning()) {
                logger.info("⚠ Daemon is already running on port $PORT")
                return false
            }

            val classPath = listOf(
                "C:\\projects\\nabu\\nabu-daemon\\target\\nabu-daemon-0.1.0-SNAPSHOT\\*"
            ).joinToString(separator = File.pathSeparator)

            // Start proces
            val pb = ProcessBuilder(
                "java",
                "--class-path",
                classPath,
                "io.github.potjerodekool.nabu.compiler.daemon.LightweightCompilerDaemon"
            )
            //pb.redirectOutput(ProcessBuilder.Redirect.INHERIT)
            //pb.redirectError(ProcessBuilder.Redirect.INHERIT)
            pb.redirectOutput(File("C:\\projects\\nabu-idea\\out.log"))
            pb.redirectError(File("C:\\projects\\nabu-idea\\error.log"))

            daemonProcess = pb.start()

            logger.info("Daemon process started (PID: " + daemonProcess.pid() + ")")


            // Wacht tot daemon klaar is
            if (waitForDaemon(STARTUP_TIMEOUT)) {
                logger.info("✓ Daemon is ready")

                // Save PID voor later gebruik
                savePID(daemonProcess.pid())

                return true
            } else {
                logger.log(Level.WARNING, "✗ Daemon failed to start")
                daemonProcess.destroyForcibly()
                return false
            }
        } catch (e: java.lang.Exception) {
            logger.log(Level.SEVERE,"Failed to start daemon: " + e.message)
            return false
        }
    }


    private fun deletePID() {
        try {
            Files.deleteIfExists(Paths.get("daemon.pid"))
        } catch (e: IOException) {
            // Ignore
        }
    }

    private fun savePID(pid: Long) {
        try {
            Files.writeString(Paths.get("daemon.pid"), pid.toString())
        } catch (e: IOException) {
            logger.log(Level.SEVERE,"Warning: Could not save PID file: " + e.message)
        }
    }
}