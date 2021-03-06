package eu.integrable.linuxresourcewatcher.watchers

import eu.integrable.linuxresourcewatcher.core.Memory
import eu.integrable.linuxresourcewatcher.core.ProcessCommand
import eu.integrable.linuxresourcewatcher.core.Report

class NvidiaCudaWatcher {

    Report<Boolean> isNvidiaSmiAvailable() {
        try {
            def value = new ProcessCommand("nvidia-smi").by {}
        } catch (IOException ex) {
            return new Report<>(false)
        }
        return new Report<>(true)
    }

    Report<Memory> getUsedMemory(Integer gpuId) {
        def value = new ProcessCommand("nvidia-smi --query-gpu=memory.used --format=csv -i $gpuId").by {
            def line = it.split("\n")[1]
            Long.valueOf(line.split(" ")[0].trim()) * 1024
        }
        return new Report<>(new Memory(value))
    }

    Report<Integer> getNumberOfGpus() {
        def value = new ProcessCommand("nvidia-smi --query-gpu=name --format=csv").by {
            it.split("\n").size() - 1
        }
        return new Report<>(value)
    }

    Report<Memory> getFreeMemory(Integer gpuId) {
        def value = new ProcessCommand("nvidia-smi --query-gpu=memory.free --format=csv -i $gpuId").by {
            def line = it.split("\n")[1]
            Long.valueOf(line.split(" ")[0].trim()) * 1024
        }
        return new Report<>(new Memory(value))
    }

    Report<Memory> getTotalMemory(Integer gpuId) {
        def value = new ProcessCommand("nvidia-smi --query-gpu=memory.total --format=csv -i $gpuId").by {
            def line = it.split("\n")[1]
            Long.valueOf(line.split(" ")[0].trim()) * 1024
        }
        return new Report<>(new Memory(value))
    }

    Report<Integer> getTemperature(Integer gpuId) {
        def value = new ProcessCommand("nvidia-smi --query-gpu=temperature.gpu --format=csv -i $gpuId").by {
            def line = it.split("\n")[1]
            Long.valueOf(line.split(" ")[0].trim())
        }
        return new Report<>(value)
    }

    Report<Integer> getUtilization(Integer gpuId) {
        def value = new ProcessCommand("nvidia-smi --query-gpu=utilization.gpu --format=csv -i $gpuId").by {
            def line = it.split("\n")[1]
            Long.valueOf(line.split(" ")[0].trim())
        }
        return new Report<>(value)
    }

    Report<String> getName(Integer gpuId) {
        def value = new ProcessCommand("nvidia-smi --query-gpu=name --format=csv -i $gpuId").by {
            def name = it.split("\n")[1]
            name.trim()
        }
        return new Report<>(value)
    }
}
