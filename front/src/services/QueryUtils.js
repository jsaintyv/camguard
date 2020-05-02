import jQuery from "jquery";

// I'm using jQuery because he is already available in dependency due to bootstrap.

export default {
    post(url, data) {
        return jQuery.ajax ({
            url: url,
            type: "POST",
            data: JSON.stringify(data),
            dataType: "json",
            contentType: "application/json; charset=utf-8",
        });
    },
    get(url, optionalData) {
        if(optionalData == null) {
            return jQuery.get(url);
        } else {
            return jQuery.get(url, optionalData);
        }

    },
    delete(url) {
        return jQuery.ajax({url: url ,   method: "DELETE"});
    }
};