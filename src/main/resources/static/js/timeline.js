$(document)
    .ready(
        function () {
            handleClickableToSwitch($(".btn-timeline"));
            handleTimelineDropdownFiltering($(".filter"));
        });

function handleClickableToSwitch(clickableElement){
    clickableElement.click(function () {
        $(this).toggleClass('clicked');
        $(`h5#${this.id}-header`).toggleClass('clicked');
    });
}

function handleTimelineDropdownFiltering(filterCheckbox){
    filterCheckbox.click(function () {
        const timeLineButtons = $(`.${this.id}`);
        const clickedFilterCheckboxElement=$(this)[0];

        timeLineButtons.each(function () {
            const timeLineButton = $(this);

            if (clickedFilterCheckboxElement.checked && !(timeLineButton.hasClass('clicked'))) {
                timeLineButton.trigger('click');
            }

            if (!(clickedFilterCheckboxElement.checked) && (timeLineButton.hasClass('clicked'))) {
                timeLineButton.trigger('click');
            }
        });
    });
}