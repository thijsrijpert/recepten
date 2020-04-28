<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
require_once(dirname(__FILE__, 6) . '\server\api\TimeOfDay.php');
require_once(dirname(__FILE__, 6) . '\server\exception\NullPointerException.php');

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
