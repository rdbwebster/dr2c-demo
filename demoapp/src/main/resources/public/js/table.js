$("#sample").colResizable({liveDrag:true});

$("#sample2").colResizable({
    liveDrag:true,
    gripInnerHtml:"<div class='grip'></div>", 
    draggingClass:"dragging" });


$("#nonFixedSample").colResizable({
    fixed:false,
    liveDrag:true,
    gripInnerHtml:"<div class='grip2'></div>", 
    draggingClass:"dragging" 
});