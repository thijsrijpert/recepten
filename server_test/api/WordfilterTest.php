<?php
declare(strict_types=1);

require_once(dirname(__FILE__, 3) . '\server\api\Wordfilter.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');

use PHPUnit\Framework\TestCase;
use exception\NullPointerException;

final class WorldfilterApiTest extends TestCase
{

    protected $wordfilter;

    public function setUp() : void{
        $this->wordfilter = new api\Wordfilter();
    }

    public function testErrorHandler(): void
    {
        $this->expectException(NullPointerException::class);
        $this->wordfilter->error_handler(1, 'Undefined index: word', 1, 1);
    }
}
