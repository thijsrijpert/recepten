<?php
declare(strict_types=1);

require_once(dirname(__FILE__, 3) . '\server\api\Religion.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');

use PHPUnit\Framework\TestCase;
use exception\NullPointerException;

final class ReligionApiTest extends TestCase
{

    protected $religion;

    public function setUp() : void{
        $this->religion = new api\Religion();
    }

    public function testErrorHandler(): void
    {
        $this->expectException(NullPointerException::class);
        $this->religion->error_handler(1, 'Undefined index: name', 1, 1);
    }
}
