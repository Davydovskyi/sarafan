axios.defaults.baseURL = 'http://localhost:8080';

const MessageForm = {
    props: ['messages', 'messageAttribute'],
    data() {
        return {
            text: '',
            editing: false
        }
    },
    watch: {
        messageAttribute(newVal) {
            this.text = newVal.text
            this.editing = true
        }
    },
    template: `<form @submit.prevent="onSubmit">
                    <div class="form-group">
                        <textarea class="form-control" placeholder="text" rows="3" v-model="text"></textarea>
                    </div>
                    <button class="btn btn-primary" type="submit">Save</button>
               </form>`,
    methods: {
        async onSubmit() {
            try {
                if (this.editing) {
                    const response = await axios.put(`/messages/${this.messageAttribute.id}`, {text: this.text});
                    const index = this.messages.findIndex(m => m.id === response.data.id);
                    this.messages.splice(index, 1, response.data);
                    console.log(response);
                } else {
                    const response = await axios.post('/messages', {text: this.text});
                    this.messages.push(response.data);
                    console.log(response);
                }
                this.editing = false;
                this.text = '';
            } catch (e) {
                console.error('Error submitting message:', e);
                alert(e.response.data.message);
            }
        }
    }
}

const MessageRow = {
    props: ['message', 'updateMethod', "messages"],
    template: `<div><i>{{ message.id }}. </i> {{ message.text }}
                   <span style="position: absolute; right: 0">
                        <button @click='update' class="btn btn-warning btn-sm">Update</button>
                        <button @click='del(message.id)' class="btn btn-danger btn-sm">Delete</button>
                   </span>
               </div>`,
    methods: {
        update() {
            this.updateMethod(this.message);
        },
        async del(id) {
            try {
                const response = await axios.delete(`/messages/${id}`);
                console.log(response);
                if (response.status === 204) {
                    const index = this.messages.findIndex(m => m.id === id);
                    this.messages.splice(index, 1);
                }
            } catch (e) {
                console.error('Error deleting message:', e);
                alert(e.response.data.message);
            }
        }
    }
}

const MessageList = {
    props: ['messages'],
    data() {
        return {
            message: null
        }
    },
    template: `<div style="position: relative; width: 300px;">
                    <message-form :messageAttribute="message" :messages="messages"/>
                    <template :key="message.id" v-for="message in messages">
                         <message-row :message="message" :updateMethod="updateMethod" :messages="messages"/>
                    </template>
                </div>`,
    components: {
        'message-form': MessageForm,
        'message-row': MessageRow
    },
    async created() {
        try {
            const response = await axios.get('/messages');
            response.data.forEach(message => {
                this.messages.push(message);
            })
        } catch (e) {
            console.error('Error fetching messages:', e);
            alert(e.response.data.message);
        }
    },
    methods: {
        updateMethod: function (message) {
            this.message = message;
        }
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