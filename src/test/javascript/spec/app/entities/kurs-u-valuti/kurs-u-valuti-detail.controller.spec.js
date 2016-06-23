'use strict';

describe('Controller Tests', function() {

    describe('KursUValuti Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockKursUValuti, MockValuta, MockKursnaLista;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockKursUValuti = jasmine.createSpy('MockKursUValuti');
            MockValuta = jasmine.createSpy('MockValuta');
            MockKursnaLista = jasmine.createSpy('MockKursnaLista');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'KursUValuti': MockKursUValuti,
                'Valuta': MockValuta,
                'KursnaLista': MockKursnaLista
            };
            createController = function() {
                $injector.get('$controller')("KursUValutiDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pinfProApp:kursUValutiUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
