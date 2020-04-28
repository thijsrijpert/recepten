<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
require_once(dirname(__FILE__, 3) . '\server\api\Api.php');

final class ReviewTest extends TestCase
{

    protected $api;

    public function setUp() : void{
        $this->api = new api\Review();
    }

    public function testSetHttpCodeOK(): void
    {
        $this->api->setHttpCode('00');

        assertEquals(
            200,
            http_response_code()
        );
    }

    public function testSetHttpCodeWarning(): void
    {
        $this->api->setHttpCode('01');

        assertEquals(
            200,
            http_response_code()
        );
    }

    public function testSetHttpCodeNoData(): void
    {
        $this->api->setHttpCode('02');

        assertEquals(
            404,
            http_response_code()
        );
    }

    public function testSetHttpCodeDynamicSql(): void
    {
        $this->api->setHttpCode('07');

        assertEquals(
            500,
            http_response_code()
        );
    }

    public function testSetHttpCodeConnectionFailed(): void
    {
        $this->api->setHttpCode('08');

        assertEquals(
            503,
            http_response_code()
        );
    }

    public function testSetHttpCodeData(): void
    {
        $this->api->setHttpCode('22');

        assertEquals(
            400,
            http_response_code()
        );
    }

    public function testSetHttpCodeConstraint(): void
    {
        $this->api->setHttpCode('23');

        assertEquals(
            400,
            http_response_code()
        );
    }

    public function testSetHttpCodeLogin(): void
    {
        $this->api->setHttpCode(2002);

        assertEquals(
            503,
            http_response_code()
        );
    }
}
