<template>

    <v-data-table
        :headers="headers"
        :items="topFood"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'TopFoodView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
                { text: "name", value: "name" },
                { text: "count", value: "count" },
                { text: "score", value: "score" },
            ],
            topFood : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/topFoods'))

            temp.data._embedded.topFoods.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.topFood = temp.data._embedded.topFoods;
        },
        methods: {
        }
    }
</script>

