const htmlBody=$('body');

function setResizeObserver(target, resizeMutationCallbacks) {
    const observer = new ResizeObserver(resizeMutationCallbacks);

    htmlBody.on("beforePrint", function () {
        observer.unobserve(target);
        $('body').trigger("resizeObserversOnSleep");
    })
    htmlBody.on("afterPrint", function () {
        observer.observe(target);
    })

    observer.observe(target);
}

function setMutationObserver(target, mutationCallbacks) {
    const observer = new MutationObserver(mutationCallbacks);
    const config = {attributes: true, childList: true, subtree: true};
    observer.observe(target, config)

    htmlBody.on("beforePrint", function () {
        observer.disconnect();
        $('body').trigger("mutationObserversOnSleep");
    })
    htmlBody.on("afterPrint", function () {
        observer.observe(target, config);
    })
}

export {setMutationObserver, setResizeObserver}
