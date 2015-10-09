'use strict';

describe('Controller: DirectivoCtrl', function () {

  // load the controller's module
  beforeEach(module('clientAppApp'));

  var DirectivoCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    DirectivoCtrl = $controller('DirectivoCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(DirectivoCtrl.awesomeThings.length).toBe(3);
  });
});
