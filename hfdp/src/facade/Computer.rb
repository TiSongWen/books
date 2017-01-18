class CPU
    def execute; end
end

class Memory
    def load; end
end

class HardDrive
    def read; end
end

class Computer
    def initialize
        @cpu = CPU.new
        @mem = Memory.new
        @hd  = HardDrive.new
    end

    def start
        @cpu.execute
        @mem.load
        @hd.read
    end
end

computerFacade = Computer.new
computerFacade.start
