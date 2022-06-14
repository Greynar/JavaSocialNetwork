<template>
    <v-layout row>
        <v-text-field
            label="Новое сообщение"
            placeholder="Введите сообщение"
            v-model="text"
            @keyup.enter="save"/>
        <v-btn @click="save" rounded>
            Сохранить
        </v-btn>
    </v-layout>
</template>

<script>
import { mapActions } from 'vuex'

export default {
    props: ['messageAttribute'],
    data: function() {
        return {
            text: '',
            id: null
        }
    },
    watch: {
        messageAttribute(newVal, oldVal) {
            this.text = newVal.text
            this.id = newVal.id
        }
    },
    methods: {
        ...mapActions(['addMessageAction', 'updateMessageAction']),
        save() {
            const message = {
                id: this.id,
                text: this.text
            }
            if (this.id) {
                this.updateMessageAction(message)
            } else {
                this.addMessageAction(message)
            }
            this.text = ''
            this.id = null
        }
    }
}
</script>

<style scoped>

</style>