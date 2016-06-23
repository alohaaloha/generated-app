'use strict';

describe('Controller Tests', function() {

    describe('KursnaLista Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockKursnaLista, MockBanka, MockKursUValuti;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockKursnaLista = jasmine.createSpy('MockKursnaLista');
            MockBanka = jasmine.createSpy('MockBanka');
            MockKursUValuti = jasmine.createSpy('MockKursUValuti');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'KursnaLista': MockKursnaLista,
                'Banka': MockBanka,
                'KursUValuti': MockKursUValuti
            };
            createController = function() {
                $injector.get('$controller')("KursnaListaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pinfProApp:kursnaListaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
