/**
 * 
 */

$(function() {
    $(".my-PHONE").inputmask({mask: '(999) 999 99 99'});
    $(".my-PHONE7").inputmask({mask: '999 99 99'});
    $(".my-DATE").inputmask({alias: "datetime", inputFormat: "dd.mm.yyyy"});
    $(".my-TIME").inputmask({alias: "datetime", inputFormat: "HH:MM"});


    
});