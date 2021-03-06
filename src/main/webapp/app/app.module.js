(function() {
    'use strict';

    angular
        .module('pinfProApp', [
            'ngStorage',
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar',
            'ngFileUpload'
        ])
        .run(run);

    run.$inject = ['stateHandler', '$rootScope', '$templateCache'];

    function run(stateHandler, $rootScope, $templateCache) {
        stateHandler.initialize();

         /* ZOOM */
        /*ALL ALL ENTITIES HERE AND SET IT TO NULL*/
        $rootScope.naseljenoMesto=null;
        $rootScope.naseljenoMestoZOOM=false;

        $rootScope.drzava=null;
        $rootScope.drzavaZOOM=false;


        $rootScope.klijent=null;
        $rootScope.klijentZOOM=false;

        $rootScope.valuta = null;
        $rootScope.valutaZOOM = false;


        $rootScope.bankaZOOM = false;
        $rootScope.racunPravnogLica = null;
    }
})();
