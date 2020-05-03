<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
require_once(dirname(__FILE__, 3) . '\server\api\Api.php');

final class ApiTest extends TestCase
{

    protected $api;

    public function setUp() : void{
        $this->api = new api\Api();
    }

    // /**
    //  * @runInSeparateProcess
    //  */
    // public function testSetHttpCodeOK(): void
    // {
    //     $this->api->setHttpCode('00');
    //
    //     $this->assertEquals(
    //         200,
    //         http_response_code()
    //     );
    // }
    // /**
    //  * @runInSeparateProcess
    //  */
    // public function testSetHttpCodeWarning(): void
    // {
    //     $this->api->setHttpCode('01');
    //     var_dump(http_response_code());
    //
    //     $this->assertEquals(
    //         200,
    //         http_response_code()
    //     );
    // }
    // /**
    //  * @runInSeparateProcess
    //  */
    // public function testSetHttpCodeNoData(): void
    // {
    //     $this->api->setHttpCode('02');
    //
    //   $this->assertEquals(
    //         404,
    //         http_response_code()
    //     );
    // }
    // /**
    //  * @runInSeparateProcess
    //  */
    // public function testSetHttpCodeDynamicSql(): void
    // {
    //     $this->api->setHttpCode('07');
    //
    //     $this->assertEquals(
    //         500,
    //         http_response_code()
    //     );
    // }
    // /**
    //  * @runInSeparateProcess
    //  */
    // public function testSetHttpCodeConnectionFailed(): void
    // {
    //     $this->api->setHttpCode('08');
    //
    //     $this->assertEquals(
    //         503,
    //         http_response_code()
    //     );
    // }
    // /**
    //  * @runInSeparateProcess
    //  */
    // public function testSetHttpCodeData(): void
    // {
    //     $this->api->setHttpCode('22');
    //
    //     $this->assertEquals(
    //         400,
    //         http_response_code()
    //     );
    // }
    // /**
    //  * @runInSeparateProcess
    //  */
    // public function testSetHttpCodeConstraint(): void
    // {
    //     $this->api->setHttpCode('23');
    //
    //     $this->assertEquals(
    //         400,
    //         http_response_code()
    //     );
    // }
    // /**
    //  * @runInSeparateProcess
    //  */
    // public function testSetHttpCodeLogin(): void
    // {
    //     $this->api->setHttpCode(2002);
    //
    //     $this->assertEquals(
    //         503,
    //         http_response_code()
    //     );
    // }


}
?>
