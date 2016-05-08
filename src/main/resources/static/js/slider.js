var min2 = new Date("2015-04-29"),
    max2 = new Date("2015-04-30");

function addZero(val) {
    if (val < 10) {
        return "0" + val;
    }

    return val;
}

$("#slider").dateRangeSlider({
    bounds: {
        "min": min2,
        "max": max2
    },
    formatter: function (val) {
        var h = val.getHours(),
            m = val.getMinutes();
        return addZero(h) + ':' + addZero(m);
    },
    defaultValues: {
        min: new Date("2015-04-01T05:00:00Z"),
        max: new Date("2015-04-01T24:00:00Z")
    }
});