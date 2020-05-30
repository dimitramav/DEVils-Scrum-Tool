<template>
    <h6 class="text-muted">
        <i>{{ priorityToString(cur_pbi_priority) }}</i>
    </h6>
</template>

<script>
export default {
    name: 'PriorityFooter',
    props: {
        cur_pbi_priority: Number,
    },
    methods: {
        priorityToString(priority) {
            let str_priority
            switch (priority) {
                case 1:
                    str_priority = 'high'
                    break
                case 2:
                    str_priority = 'medium'
                    break
                case 3:
                    str_priority = 'low'
                    break
                default:
                    console.log('Unknown value' + priority)
            }
            return str_priority
        },
    },
}
</script>

<style scoped></style>
