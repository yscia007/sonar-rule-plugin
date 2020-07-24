
class AException {}
class BException extends NullPointerException {}
class CException extends UnknownException {}
class DException extends CException {}
class EException extends Exception {}

class ECeption extends Exception {}    // Noncompliant

class SException extends AException {}

class ExceptionHandler {}
class CacheDemoException extends Exception {}

class BusinessException extends ClientException{}

class BusinessExceptions extends ClientException{}  // Noncompliant

class ClientException {}

class SysException extends ClientException{}

