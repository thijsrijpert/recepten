<?php
declare(strict_types=1);
use PHPUnit\Framework\TestCase;
require_once(dirname(__FILE__, 3) .'\server\model\TimeOfDay.php');
require_once(dirname(__FILE__, 3) . '\server\database\TimeOfDay.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');

final class TimeOfDayDatabaseTest extends TestCase
{

    protected $timeOfDay;

    public function setUp() : void{
        $this->timeOfDay = new database\TimeOfDay();
    }

    public function testInsert(): void
    {

        $this->assertEquals(
            '00000',
            $this->timeOfDay->insert(new \model\TimeOfDay('tussendoortje'))

        );
        $this->assertEquals(
            '23000',
            $this->timeOfDay->insert(new \model\TimeOfDay('tussendoortje'))

        );
        $this->assertEquals(
            '22001',
            $this->timeOfDay->insert(new \model\TimeOfDay('THHHHHHHHHHHHHHIIIIIIIIIIIIIIIIIIIIISSSSSSSSSSSSSSSSSSSSSSSSSSIIIIIIIIIIIIIIISSSSSSSSSSSSSSSSLLLLLLONNNNNNG')),

        );
    }
}
