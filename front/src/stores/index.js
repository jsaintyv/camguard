import Vue from 'vue';
import Vuex from 'vuex';
import WebcamService from "../services/WebcamService.js";

Vue.use(Vuex);

export const UPDATE_WEBCAMS = "UPDATE_WEBCAMS";
export const UPDATE_IMAGES_LIST = "UPDATE_IMAGES_LIST";
export const UPDATE_TIME_RANGE = "UPDATE_TIME_RANGE";

export const ACTION_LOAD_WEBCAMS = "ACTION_LOAD_WEBCAMS";
export const ACTION_LOAD_IMAGES = "ACTION_LOAD_IMAGES";
export const ACTION_UPDATE_TIME_RANGE = "ACTION_UPDATE_TIME_RANGE";

export default new Vuex.Store({
    state: {
        webcams: [],
        imagesByWebcamName: {},
        timeRange: {
            start: new Date(Date.now()-(600 * 1000)),
            end: new Date()
        }
    },    
    mutations: { 
        [UPDATE_WEBCAMS] (state, webcams) {
            console.log(webcams);
            state.webcams = webcams;            
        },        
        [UPDATE_IMAGES_LIST] (state, newPatch) {
            console.log(UPDATE_IMAGES_LIST, newPatch);                
            state.imagesByWebcamName = Object.assign({}, state.imagesByWebcamName, newPatch);            
        },
        [UPDATE_TIME_RANGE] (state, range) {                        
            state.timeRange = range;
        }
    },
    actions: {        
        [ACTION_LOAD_WEBCAMS] (store) {
            console.log(ACTION_LOAD_WEBCAMS);
            WebcamService.listWebcams()
            .done(list => { 
                store.commit(UPDATE_WEBCAMS, list);
                // On charge la liste des images.
                list.forEach(webcamName => {
                    store.dispatch(ACTION_LOAD_IMAGES, webcamName);    
                });
                
            });
        },
        [ACTION_LOAD_IMAGES] (store, webcamName) {
            console.log(ACTION_LOAD_IMAGES);
            WebcamService.listImages(webcamName, store.state.timeRange.start.valueOf(), store.state.timeRange.end.valueOf())
            .done(list => {
                console.log("loading images list", list);
                var patch = {};
                patch[webcamName] = list.map((filename)=> "/api/images/retrieve/" + filename);
                store.commit(UPDATE_IMAGES_LIST, patch);
            });            
        },
        [ACTION_UPDATE_TIME_RANGE] (store, range) {
            store.commit(UPDATE_TIME_RANGE, range);
            store.state.webcams.forEach(webcamName => store.dispatch(ACTION_LOAD_IMAGES, webcamName));
        }        
    }

});
