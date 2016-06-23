(function() {
    'use strict';
    angular
        .module('pinfProApp')
        .factory('KursnaLista', KursnaLista);

    KursnaLista.$inject = ['$resource', 'DateUtils'];

    function KursnaLista ($resource, DateUtils) {
        var resourceUrl =  'api/kursna-listas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.datum = DateUtils.convertDateTimeFromServer(data.datum);
                    data.datumPrimene = DateUtils.convertDateTimeFromServer(data.datumPrimene);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
