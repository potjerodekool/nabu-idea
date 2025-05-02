package io.github.potjerodekool.nabuidea.language.structure

import com.intellij.util.messages.MessageBusConnection
import com.intellij.util.messages.MessageHandler
import com.intellij.util.messages.Topic

class MessageBusConnectionMock : MessageBusConnection {
    override fun <L : Any> subscribe(topic: Topic<L>) {
    }

    override fun setDefaultHandler(handler: MessageHandler?) {
    }

    override fun deliverImmediately() {
    }

    override fun <L : Any> subscribe(topic: Topic<L>, handler: L) {
    }

    override fun disconnect() {
    }

    override fun dispose() {
    }

}

