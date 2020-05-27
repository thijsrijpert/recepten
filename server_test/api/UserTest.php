<?php
declare(strict_types=1);

require_once(dirname(__FILE__, 3) . '\server\api\User.php');
require_once(dirname(__FILE__, 3) . '\server\exception\NullPointerException.php');

use PHPUnit\Framework\TestCase;
use exception\NullPointerException;

final class UserApiTest extends TestCase
{

    protected $user;

    public function setUp() : void{
        $this->user = new api\User();
    }

    public function testErrorHandler(): void
    {
        $this->expectException(NullPointerException::class);
        $this->user->error_handler(1, 'Undefined index: username', 1, 1);
    }


}
