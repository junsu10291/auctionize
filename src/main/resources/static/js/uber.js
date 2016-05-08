function getUberPrice(startLat, startLng, endLat, endLng) {
    $.ajax({
        url: "https://api.uber.com/v1/estimates/price",
        headers: {
            Authorization: "Token " + "_jOzhJLJy-WM0eV74N9d3CA4vZqGkJd4R4-KFlFu"
        },
        data: {
            start_latitude: startLat,
            start_longitude: startLng,
            end_latitude: endLat,
            end_longitude: endLng
        },
        success: function (result) {
            return result.prices[0].low_estimate;
        }
    });
}