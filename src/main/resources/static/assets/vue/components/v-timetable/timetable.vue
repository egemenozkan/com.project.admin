<template>
    
</template>

<script>
var blankTimeTable = { "id": 0, "pageId": document.getElementById("eventId").value, "pageType": "EVENT", "periodType": "MONDAYS", "startDate": "", "startTime": "", "endDate": "", "endTime": "", "showStartTime": true, "showEndTime": true, "twentyFourSeven": false, "showStartDate": true, "showEndDate": true };

function dateFormat(date) {
    if (typeof date != 'undefined' && date != null && date.length == 10) {
        return date.split("-")[2] + '.' + date.split("-")[1] + '.' + date.split("-")[0]
    }
    return "";
}

function filterTimeTable(data) {
    if (typeof data == 'undefined' || data == null || data.length == 0) {
        return [blankTimeTable];
    }

    return data.map(item => {
        item.startDate = dateFormat(item.startDate);
        item.endDate = dateFormat(item.endDate);
        return item;
    })
}

export default {
    name: 'timetable',
    data() {
        return {
            id: '',
            rows: []
        }
    },
    /*  filters: {
          dateWithPoint: function (date) {
              console.log(date);
              if (date.length == 10) {
                  return date.split("-")[2] + '.' + date.split("-")[1] + '.' + date.split("-")[0]
              }
              return "";
              }

      }, */
    mounted() {
        var self = this;
        self.eventId = document.getElementById("eventId").value;
        const requestUrl = '/events/' + self.eventId + '/time-table';
        axios
            .get(requestUrl)
            .then(response => (this.rows = filterTimeTable(response.data)))
    },
    methods: {
        save: function(index) {
            const requestUrl = "/events/time-table";
            console.log(index);
            axios.post(requestUrl, this.rows[index])
                .then(function(response) {
                    console.log(response);
                })
                .catch(function(error) {
                    console.log(error);
                });
        },
        add: function() {
            this.rows.push(blankTimeTable);
        },
        remove: function(index) {

            var self = this;
            const requestUrl = '/events/time-table/' + self.rows[index].id;
            axios.delete(requestUrl).then(function(response) {
                    self.rows.splice(index, 1);
                    console.log(response);
                })
                .catch(function(error) {
                    console.log(error);
                });
        }

    }


}
</script>

<style>

</style>