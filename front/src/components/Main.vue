<template>
  <div id="board">
    <b-row>
      <TimeRange />
    </b-row>

    <div v-for="webcamName in getWebcams" v-bind:key="webcamName">
            <h1>{{webcamName}}</h1>
            <div>
              <b-card no-body>
                <b-tabs content-class="mt-3">
                  <b-tab title="Direct" active>
                    <img :src="'/api/images/direct/' + webcamName + '?timeIndex=' + timerIndex" width="640" style="margin:auto;"/> 
                  </b-tab>
                  <b-tab title="Historique">
                    <Webcam v-bind:webcamName="webcamName" />  
                  </b-tab>
                  <b-tab title="Alerts">
                    <AlertsWebcam v-bind:webcamName="webcamName" />  
                  </b-tab>
                </b-tabs>            
              </b-card>
            </div>
    </div>
  </div>
</template>

<script>
  import {ACTION_LOAD_WEBCAMS} from '../stores/index'
  import Webcam from './Webcam'
  import AlertsWebcam from './AlertsWebcam'
  import TimeRange from './TimeRange'

  export default {
    name: 'Main',
    data: function(){
  	  return{
      	timerIndex: Date.now(),        
      };
    },
    mounted() {
      this.interval = setInterval(() => {
              this.timerIndex = Date.now();
      }, 1000);
    },
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
      TimeRange,
      AlertsWebcam
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