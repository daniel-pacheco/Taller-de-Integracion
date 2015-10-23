'use strict';

describe('Controller: MateriasCtrl', function () {

  // load the controller's module
  beforeEach(module('clientAppApp'));

  var MateriasCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    MateriasCtrl = $controller('MateriasCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(MateriasCtrl.awesomeThings.length).toBe(3);
  });
});
