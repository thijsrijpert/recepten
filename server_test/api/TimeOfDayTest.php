<?php
declare(strict_types=1);

require_once(dirname(__FILE__, 3) . '\server\api\TimeOfDay.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');

use PHPUnit\Framework\TestCase;
use exception\NullPointerException;

final class TimeOfDayApiTest extends TestCase
{

    protected $timeOfDay;

    public function setUp() : void{
        $this->timeOfDay = new api\TimeOfDay();
    }

    public function testErrorHandler(): void
    {
        $this->expectException(NullPointerException::class);
        $this->timeOfDay->error_handler(1, 'Undefined index: name', 1, 1);
    }


}
