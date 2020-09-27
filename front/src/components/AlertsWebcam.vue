<template>
  <div id="alerts">      
    <div v-if="getAlerts == null">                     
      Loading... No alert done for {{webcamName}}
    </div>

    <div class="rowSnapshot">                    
      <img class="images" v-for="(alert, i) in getAlerts" v-bind:key="alert.fileName" :src="alert.imageUrl" width="200" :title="alert.fileName" @click="index = i"/>                
    </div>
    <GalerySlideShowVue :images="getUrls" :index="index" @close="index = null" />
    
  </div>  
</template>

<script>    
  import GalerySlideShowVue from './GalerySlideShow.vue';
  import {ACTION_LOAD_ALERTS} from '../stores/index'

  export default {
    name: 'AlertsWebcam',    
    data: () => {    
      return {
        index: null
      };
    },
    props: ['webcamName'],    
    computed: {    
        getAlerts() {            
          return this.$store.state.alertsByWebcamName[this.webcamName];
        },
        getUrls() {            
          return this.$store.state.alertsByWebcamName[this.webcamName].map((a)=>a.imageUrl);
        }
    },    
    mounted() {
      console.log(ACTION_LOAD_ALERTS);      
      this.$store.dispatch(ACTION_LOAD_ALERTS, this.webcamName);
    },
    components: {
      GalerySlideShowVue
    } 
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>