soundManager.useFlashBlock = false;
soundManager.bgColor = '#ffffff';
soundManager.debugMode = false;
soundManager.url = '/swf/';
soundManager.wmode = 'transparent'; // hide initial flash of white on everything except firefox/win32
soundManager.allowScriptAccess = 'always';
soundManager.useFastPolling = true;
soundManager.flashVersion = 9;
soundManager.flashLoadTimeout = 3000;
soundManager.useHTML5Audio = true;

soundManager.onready(function() {
    soundManager.createSound({
        id : 'fillboard',
        url : '/sound/boardfill.mp3',
        autoLoad: true,
        autoPlay: false
    });

    soundManager.createSound({
        id : 'wronganswer',
        url : '/sound/wrong.mp3',
        autoLoad: true,
        autoPlay: false
    });

    soundManager.createSound({
        id : 'timerexpired',
        url : '/sound/timer-expired.mp3',
        autoLoad: true,
        autoPlay: false
    });

    soundManager.createSound({
        id : 'woosh',
        url : '/sound/logowoosh.mp3',
        autoLoad: true,
        autoPlay: false
    });

    soundManager.createSound({
        id : 'buzzin',
        url : '/sound/ringin.mp3',
        autoLoad: true,
        autoPlay: false
    });

    soundManager.createSound({
        id : 'correctanswer',
        url : '/sound/dailydouble.mp3',
        autoLoad: true,
        autoPlay: false
    });

    soundManager.createSound({
        id : 'final',
        url : '/sound/final.mp3',
        autoLoad: true,
        autoPlay: false
    });

    soundManager.createSound({
        id : 'ending',
        url : '/sound/ending.mp3',
        autoLoad: true,
        autoPlay: false
    });
});