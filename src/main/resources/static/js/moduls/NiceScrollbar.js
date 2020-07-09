class NiceScrollbar{
    constructor(wrapperElement) {
        this.scrollbarInstance=wrapperElement.niceScroll({
            cursorwidth: '10px',
            cursoropacitymax: 0,
            zindex: 1001
        });
    }

    resize(){

    }
}