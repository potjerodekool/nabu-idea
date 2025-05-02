package io.github.potjerodekool.nabuidea.language.structure

import com.intellij.openapi.Disposable
import com.intellij.util.messages.MessageBus
import com.intellij.util.messages.MessageBusConnection
import com.intellij.util.messages.SimpleMessageBusConnection
import com.intellij.util.messages.Topic
import kotlinx.coroutines.CoroutineScope

class MessageBusMock(
    override val parent: MessageBus?,
    override val isDisposed: Boolean
) : MessageBus {
    override fun connect(): MessageBusConnection {
        return MessageBusConnectionMock()
    }

    override fun simpleConnect(): SimpleMessageBusConnection {
        return MessageBusConnectionMock()
    }

    override fun connect(parentDisposable: Disposable): MessageBusConnection {
        TODO("Not yet implemented")
    }

    override fun connect(coroutineScope: CoroutineScope): SimpleMessageBusConnection {
        TODO("Not yet implemented")
    }

    override fun <L : Any> syncPublisher(topic: Topic<L>): L {
        TODO("Not yet implemented")
    }

    override fun <L : Any> syncAndPreloadPublisher(topic: Topic<L>): L {
        TODO("Not yet implemented")
    }

    override fun dispose() {
        TODO("Not yet implemented")
    }

    override fun hasUndeliveredEvents(topic: Topic<*>): Boolean {
        TODO("Not yet implemented")
    }

}

