function buildToc() {
    var toc = '<ul class="toc">';
    var inInner = false;
    $("h2, h3").each(function () {
        if (this.tagName === 'H3' && !inInner) {
            inInner = true;
            toc = toc.concat('<ul>');
        }
        if (this.tagName === 'H2' && inInner) {
            inInner = false;
            toc = toc.concat('</ul>');
        }
        var text = $(this).text();
        var id = text.toLowerCase().replace(/ /g, '-');
        $(this).attr('id', id);
        toc = toc.concat('<li>').concat('<a href="#' + id + '">').concat(text).concat('</a>').concat('</li>');
    });
    toc = toc.concat('</ul>');
    $("h1").after(toc);
}

$(document).ready(buildToc);