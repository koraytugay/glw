function clipContent(id) {
    navigator.clipboard.writeText($(id).text()).then(() => alert('Copied to clipboard!'));
}

$(document).ready(
    $("pre code").each(function (index) {
        let dynamicId = 'clipboard'.concat(index);
        $(this).attr("id", dynamicId);
        $(this).before('<a href="#" onclick="clipContent(' + dynamicId + '); return false;" style="position: absolute; right: 0; margin-bottom: -20px">&#128203;</a>');
    })
);
