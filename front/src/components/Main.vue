<template>
  <div id="board">
    <b-row>
      <TimeRange />
    </b-row>

    <b-row v-for="webcamName in getWebcams" v-bind:key="webcamName">
            <h1>
                {{webcamName}}
            </h1>
            
            <Webcam v-bind:webcamName="webcamName" />
    </b-row>
  </div>
</template>

<script>
  import {ACTION_LOAD_WEBCAMS} from '../stores/index'
  import Webcam from './Webcam'
  import TimeRange from './TimeRange'

  export default {
    name: 'Main',
    created() {
      this.$store.dispatch(ACTION_LOAD_WEBCAMS);
    },
    computed: {
        getWebcams() {
            return this.$store.state.webcams;
        }    
    },
    components: {
      Webcam,
      TimeRange
    } 
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#board {
  margin-left:5px;
  margin-right:5px; 
}
</style>