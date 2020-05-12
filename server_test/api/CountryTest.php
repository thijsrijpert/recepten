<?php
declare(strict_types=1);

require_once(dirname(__FILE__, 3) . '\server\api\Country.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');

use PHPUnit\Framework\TestCase;
use exception\NullPointerException;

final class CountryApiTest extends TestCase
{

    protected $country;

    public function setUp() : void{
        $this->country = new api\Country();
    }

    public function testErrorHandler(): void
    {
        $this->expectException(NullPointerException::class);
        $this->country->error_handler(1, 'Undefined index: countrycode', 1, 1);
    }
}
