function buildToc() {
    var toc = '<span class="toc" style="padding-left: 4px"><h4 id="toc-header">Table of Contents</h4><ul>';
    var inInner = false;
    var atLeastOneItemFound = false;
    $("h2, h3").each(function () {
        if (this.tagName === 'H3' && !inInner) {
            inInner = true;
            toc = toc.concat('<ul>');
            atLeastOneItemFound = true;
        }
        if (this.tagName === 'H2' && inInner) {
            inInner = false;
            toc = toc.concat('</ul>');
            atLeastOneItemFound = true;
        }
        var text = $(this).text();
        var id = text.toLowerCase().replace(/ /g, '-');
        $(this).attr('id', id);
        toc = toc.concat('<li>').concat('<a href="#' + id + '">').concat(text).concat('</a>').concat('</li>');
    });
    toc = toc.concat('</ul></span>');
    if (atLeastOneItemFound)
        $("h1").after(toc);
}

$(document).ready(buildToc);
