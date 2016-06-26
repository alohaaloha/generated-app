(function() {
    'use strict';
    angular
        .module('pinfProApp')
        .factory('Kliring', Kliring);

    Kliring.$inject = ['$resource', 'DateUtils'];

    function Kliring ($resource, DateUtils) {
        var resourceUrl =  'api/klirings/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.datumValute = DateUtils.convertLocalDateFromServer(data.datumValute);
                        data.datum = DateUtils.convertDateTimeFromServer(data.datum);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.datumValute = DateUtils.convertLocalDateToServer(data.datumValute);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.datumValute = DateUtils.convertLocalDateToServer(data.datumValute);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
