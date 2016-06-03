'use strict';

describe('Controller: ClientappappCtrl', function () {

  // load the controller's module
  beforeEach(module('clientAppApp'));

  var ClientappappCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ClientappappCtrl = $controller('ClientappappCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(ClientappappCtrl.awesomeThings.length).toBe(3);
  });
});
