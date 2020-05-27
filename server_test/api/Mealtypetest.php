<?php
declare(strict_types=1);

require_once(dirname(__FILE__, 3) . '\server\api\Mealtype.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');

use PHPUnit\Framework\TestCase;
use exception\NullPointerException;

final class MealtypeApiTest extends TestCase
{

    protected $mealtype;

    public function setUp() : void{
        $this->mealtype = new api\Mealtype();
    }

    public function testErrorHandler(): void
    {
        $this->expectException(NullPointerException::class);
        $this->mealtype->error_handler(1, 'Undefined index: countrycode', 1, 1);
    }
}
