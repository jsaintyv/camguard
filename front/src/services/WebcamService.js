import QueryUtils from "./QueryUtils";

export default {
    listWebcams() {
        return QueryUtils.get("/api/webcams/list");
    },
    listImages(webcamName, start, end) {
        return QueryUtils.get("/api/images/list/" + webcamName, {start: start, end: end});
    },
    listAlerts(webcamName) {
        return QueryUtils.get("/api/alert/list/" + webcamName);
    }    
};
