<template>
  <div id="webcam">  
    <div class="rowSnapshot">                    
      <img class="images" v-for="(fileName, i) in getImages" v-bind:key="fileName" :src="fileName" width="200" :title="fileName" @click="index = i"/>                
    </div>
    <GalerySlideShowVue :images="getImages" :index="index" @close="index = null" />
  </div>
</template>

<script>    
  import GalerySlideShowVue from './GalerySlideShow.vue';

  export default {
    name: 'Webcam',    
    props: ['webcamName'],
    data: () => {    
      return {
        index: null
      };
    },
    computed: {
        getImagesByWebcamName() {
            console.log(this.webcamName, this.$store.state.imagesByWebcamName);
            return this.$store.state.imagesByWebcamName;
        },
        getImages() {
            console.log(this.webcamName, this.$store.state.imagesByWebcamName);
            return this.$store.state.imagesByWebcamName[this.webcamName];
        }
    },
    components: {
      GalerySlideShowVue
    } 
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#webcam {
  margin-left:5px;
  margin-right:5px; 
}

.rowSnapshot {
  display:flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  
}

.images {
  flex-direction: column;
  border-radius: 0.25rem;
  box-shadow: 0 20px 40px -14px rgba(0,0,0,0.25);
  max-width: 200px;
  max-height: 200px;
  margin:5px;

  @media(min-width: 40rem) {
    width: 50%;
  }
  @media(min-width: 56rem) {
    width: 10%;
  }

}

</style>