function refreshClapCount(response) {
    $("#current-clap-count").text(response);
}

function clapPage(path) {
    var data = {
        type: 'post',
        url: '/pageclap/clap',
        data: {
            path: path
        },
        success: refreshClapCount
    };
    $.ajax(data);
}

// Retrieves the current clap count for the current page
var data = {
    type: 'get',
    url: '/pageclap/clap-count',
    data: {
        path: window.location.pathname
    },
    success: refreshClapCount
};
$.ajax(data);