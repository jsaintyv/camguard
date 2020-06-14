<template>
  <div id="alerts">      
    <div v-if="getAlerts == null">                     
      Loading... No alert done for {{webcamName}}
    </div>
    <table>
      <tr v-for="alert in getAlerts" v-bind:key="alert.filename" >
        <td><a href="{{filename}}">{{filename}}</a></td>
        <td>{{time}}</td>
      </tr>
    </table>
  </div>  
</template>

<script>    
  import {ACTION_LOAD_ALERTS} from '../stores/index'

  export default {
    name: 'AlertsWebcam',    
    props: ['webcamName'],    
    computed: {    
        getAlerts() {            
          return this.$store.state.alertsByWebcamName[this.webcamName];
        }
    },    
    mounted() {
      console.log(ACTION_LOAD_ALERTS);      
      this.$store.dispatch(ACTION_LOAD_ALERTS, this.webcamName);
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>