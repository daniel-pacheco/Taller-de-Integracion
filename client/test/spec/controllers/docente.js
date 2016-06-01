'use strict';

describe('Controller: DocenteCtrl', function () {

  // load the controller's module
  beforeEach(module('clientAppApp'));

  var DocenteCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    DocenteCtrl = $controller('DocenteCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(DocenteCtrl.awesomeThings.length).toBe(3);
  });
});
