<template>
   <b-form inline>
    <b-input type="text" :value="getStartDate" placeholder="Date de début(aaaa-mm-dd)" ref="startDate" /> &nbsp;
    <b-input class="hourMinuteInput" type="number" :value="getStartHour" ref="startHours"  no-wheel="true" min="0" max="23" /> : <b-input class="hourMinuteInput" type="number" :value="getStartMinute" ref="startMinutes" no-wheel="true"  min="0" max="60"/>
    ->
    <b-input type="text" :value="getEndDate" placeholder="Date de fin(aaaa-mm-dd)"  ref="endDate" />  &nbsp;  
    <b-input class="hourMinuteInput" type="number" :value="getEndHour" ref="endHours" no-wheel="true"  min="0" max="23" /> : <b-input class="hourMinuteInput" type="number" :value="getEndMinute" ref="endMinutes" no-wheel="true"  min="0" max="60"/>
     <b-button variant="success" @click="updateRange">Ok</b-button>
   </b-form>
</template>

<script>      
  import DateUtils from '../utils/DateUtils'
  import {ACTION_UPDATE_TIME_RANGE} from '../stores/index'

  export default {
    name: 'TimeRange',            
    computed: {        
        getStartDate() {            
            return DateUtils.formatDateISO(this.$store.state.timeRange.start);
        },
        getStartHour() {            
            return this.$store.state.timeRange.start.getHours();
        },
        getStartMinute() {            
            return this.$store.state.timeRange.start.getMinutes();
        },
        getEndDate() {            
            return DateUtils.formatDateISO(this.$store.state.timeRange.end);
        },
        getEndHour() {            
            return this.$store.state.timeRange.end.getHours();
        },
        getEndMinute() {            
            return this.$store.state.timeRange.end.getMinutes();
        }
    },
    methods: {
        updateRange() {
            var startDate = DateUtils.convertInputValue(this.$refs.startDate.localValue);
            startDate.setHours(this.$refs.startHours.localValue);
            startDate.setMinutes(this.$refs.startMinutes.localValue);

            var endDate = DateUtils.convertInputValue(this.$refs.endDate.localValue);
            endDate.setHours(this.$refs.endHours.localValue);
            endDate.setMinutes(this.$refs.endMinutes.localValue);


            this.$store.dispatch(ACTION_UPDATE_TIME_RANGE, {"start": startDate,  "end": endDate});    
        }
    }    
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.hourMinuteInput {
  max-width: 70px;
}
</style>