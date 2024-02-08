import {Client} from "@stomp/stompjs";

const client = new Client({
    brokerURL: 'ws://localhost:8080/ws',
    debug: function () {
    }
})

const handlers = []

client.onConnect = frame => {
    client.subscribe('/topic/activity', message => {
        handlers.forEach(h => h(JSON.parse(message.body)))
    })
}

client.onWebSocketError = error => {
    console.error('Error with websocket', error)
}

client.onStompError = frame => {
    console.error('Broker reported error: ' + frame.headers['message'])
    console.error('Additional details: ' + frame.body)
}

export function addHandler(handler) {
    handlers.push(handler)
}

export function connect() {
    client.activate()
}

export function disconnect() {
    client.deactivate().then(() =>
        console.log("Disconnected"))
}

export function sendMessage(message) {
    client.publish({
        destination: "/app/change",
        body: JSON.stringify(message)
    });
}