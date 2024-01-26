axios.defaults.baseURL = 'http://localhost:8080/messages';

let allMessages = axios.get();

const MessageRow = {
    props: ['message'],
    template: `<div><i>{{ message.id }}. </i> {{ message.text }}</div>`
}

const MessageList = {
    props: ['messages'],
    template: `<message-row :key="message.id" :message="message" v-for="message in messages"/>`,
    components: {
        'message-row': MessageRow
    },
    created: function () {
        // или здесь axios.get().them
        allMessages.then(response => {
            console.log(response);
            response.data.forEach(message => {
                this.messages.push(message);
            })
        })
    }
}

const app = Vue.createApp({
    template: '<messages-list :messages="messages"/>',
    components: {
        'messages-list': MessageList
    },

    data() {
        return {
            messages: []
        };
    },
})

app.mount('#app');