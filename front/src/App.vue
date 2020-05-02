<template>
  <div id="app">    
  	<b-navbar id="bar" toggleable="lg" type="dark" variant="info">
    	<b-navbar-brand href="#">Cam Guard</b-navbar-brand>     	     	
    </b-navbar>
  	<div id="main">   	
      <router-view></router-view>
    </div>
  </div> 
</template> 

<script>

import VueRouter from 'vue-router'
import Main from './components/Main.vue'
import {mapState} from 'vuex'



const routes = [
    { path: '/', component: Main },
  ];
  
const router = new VueRouter({
    routes // short for `routes: routes`
});


export default {
  name: 'app',
  created() {    
  },  
  computed: mapState(['stateConnected']),
  router  
};
</script>

<style>
#app {
  
}

#main {
  position:absolute;
  top:70px;
  left:15px;
  right:15px;
}
</style>
