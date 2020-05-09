<?php
declare(strict_types=1);

require_once(dirname(__FILE__, 3) . '\server\api\Wordfilter.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');

use PHPUnit\Framework\TestCase;
use exception\NullPointerException;

final class WorldfilterApiTest extends TestCase
{

    protected $wordFilter;

    public function setUp() : void{
        $this->wordFilter = new api\Wordfilter();
    }

    public function testErrorHandler(): void
    {
        $this->expectException(NullPointerException::class);
        $this->wordFilter->error_handler(1, 'Undefined index: name', 1, 1);
    }
}
