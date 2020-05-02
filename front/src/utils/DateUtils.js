

class DateUtils {
    constructor() {
    }

    /**
     * Parse Date ISO string (yyyy-MM-dd) 
     * @param {String} v
     * @returns {Date} new date
     */
    convertInputValue(v) {
        var dateAsArray = (""+v).split("-");
        return  new Date(dateAsArray[0], dateAsArray[1]-1, dateAsArray[2]);
    }

    /**
     * Adds days
     * @param {Date} d 
     * @param {Int} nbDay 
     * @returns {Date} new date
     */
    addDay(d, nbDay) {
        var nd = new Date(d.valueOf());
        nd.setDate(nd.getDate()+nbDay);
        return nd;
    }

    /**
     * True , if a and b concern the same day
     * @param {Date} a 
     * @param {Date} b 
     * @returns {Boolean}
     */
    sameDay(a, b) {
        if(a == null || b == null)
            return false;

        return a.getDate() == b.getDate()
            && a.getMonth() == b.getMonth()
            && a.getYear() == b.getYear();
    }

    /**
     * Remove time part.
     * @param {Date} d 
     * @returns {Date} new date without time part.
     */
    removeTime(s) {
        var d = new Date(s.valueOf());
        d.setHours(0);
        d.setMinutes(0);
        d.setSeconds(0);
        d.setMilliseconds(0);
        return d;
    }

    /**
     * Builds an array of 7 days before d.
     * @param {Date} d
     * @returns {Date[]} 
     */
    build7DayBefore(d) {
        var end = this.removeTime(d);
        var arrayOfDay = [];
        var index = this.addDay(end, -6);
        index = this.removeTime(index);
        while(index.valueOf() <= end.valueOf()) {
            arrayOfDay.push(index);
            index = this.addDay(index, +1);            
        } 
        return arrayOfDay;
    }

    /**
     * Filters object array by filterDay.
     * @param {Date} filterDay 
     * @param {Array<Object>} array 
     * @param {Function} getterDate 
     * @returns {Array<Object>} new object array
     */
    filterByDay(filterDay, array, getterDate) {
        if(array == null) {
            return [];
        }
        return array.filter((o)=>this.sameDay(filterDay, getterDate(o)));
    }

    /**
     * Format to iso 
     * @param {Date} d 
     */
    formatDateISO(d) {
        var month = (d.getMonth()+1);
        var day = d.getDate();
        return d.getFullYear() + "-" + (month < 10 ? "0" :"") + month + "-" + (day < 10 ? "0":"") + day;
    }
}

var instance = new DateUtils();
export default instance;